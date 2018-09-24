# Automatic potato
A simple git2consul with replacement tool

Very early alpha stage

## How to run
make install && make run. Not using make?  
`docker-compose up -d` and then `mvn spring-boot:run`. Not using docker and maven?  

## Properties
| name                     | desc.                                                               | type    | default                                              |
|--------------------------|---------------------------------------------------------------------|---------|------------------------------------------------------|
| consul.host              | Consul host                                                         | String  | 127.0.0.1                                            |
| consul.port              | Consul port                                                         | Integer | 8500                                                 |
| should.clean.consul.keys | If the tool should eliminate consul non existing keys on git config | Boolean | false                                                |
| consul.prepended.to.key  | String to be prepended to every key found on git config repository  | String  | config/                                              |
| git.config.url           | Configs repository URL                                              | String  | git@github.com:williamokano/consul-config.git        |
| git.config.clone.path    | Path where the config repository will be cloned                     | String  | /tmp/gitconfig                                       |
| git.values.url           | Values repository URL                                               | String  | git@github.com:williamokano/consul-config-values.git |
| git.values.clone.path    | Path where the values repository will be cloned                     | String  | /tmp/gitvalues                                       |
| git.values.format.type   | The format of the configuration file. Not in use.                   | String  | json                                                 |

### How to override a property?
Just like any java program, use the `--property=value` option. Example:  
`java -jar automatic-potato.jar --consul.host=any.addr.fqdn --git.config.url=https://some.git.addr/repo.git`