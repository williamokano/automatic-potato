package br.com.zup.kotlingit2consul.utils

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.slf4j.LoggerFactory
import org.springframework.util.FileSystemUtils
import java.nio.file.Files
import java.nio.file.Paths

object GitUtils {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)!!

    fun pullOrClone(repositoryUrl: String, repositoryPath: String) {
        try {
            try {
                pullRepository(repositoryUrl, repositoryPath)
            } catch (e: Throwable) {
                cloneRepository(repositoryUrl, repositoryPath)
            }
        } catch (ex: Throwable) {
            LOGGER.error("Could not fetch data from git with the following message: {}", ex)
            ex.printStackTrace()
        }
    }

    fun checkout(repositoryPath: String, branch: String) {
        LOGGER.info("Checking out branch $branch")
        val git = getRepository(repositoryPath)
        git.branchCreate()
            .setForce(true)
            .setName(branch)
            .setStartPoint("origin/$branch")
            .call()
        git.checkout().setName(branch).call()
    }

    private fun getGitPath(path: String) = "$path/.git"

    private fun getRepository(path: String) =
        Git(FileRepository(getGitPath(path)))

    private fun pullRepository(repositoryUrl: String, repositoryPath: String) {
        if (Files.notExists(Paths.get(repositoryPath))) {
            throw RuntimeException("Path $repositoryPath is not a git repository")
        }

        if (Files.notExists(Paths.get(getGitPath(repositoryPath)))) {
            LOGGER.error("Path is not a git repository. Deleting...")
            FileSystemUtils.deleteRecursively(Paths.get(repositoryPath))
            throw RuntimeException("Path $repositoryPath is not a git repository")
        }

        LOGGER.info("Pulling repository $repositoryUrl into $repositoryPath")
        Git(FileRepository("$repositoryPath/.git"))
            .pull()
            .call()
        LOGGER.info("Pull finished")
    }

    private fun cloneRepository(repositoryUrl: String, repositoryPath: String) {
        LOGGER.info("Trying to clone $repositoryUrl into $repositoryPath")
        Git.cloneRepository()
            .setURI(repositoryUrl)
            .setDirectory(Paths.get(repositoryPath).toFile())
            .call()
        LOGGER.info("Clone finished")
    }
}