### Installation

Create postgresql database "tarea2" at localhost with the next owner:
* user: postgres 
* pass: postgres

Add this Datasource to the standalone.xml file of your Wildfly:
```xml
<datasource jndi-name="java:jboss/datasources/Tarea2DS" pool-name="Tarea2DS" enabled="true" use-java-context="true">
        <connection-url>jdbc:postgresql://localhost:5432/tarea2?charSet=UTF-8</connection-url>
        <driver>org.postgresql</driver>
        <transaction-isolation>TRANSACTION_READ_COMMITTED</transaction-isolation>
        <pool>
            <min-pool-size>10</min-pool-size>
            <max-pool-size>100</max-pool-size>
            <prefill>true</prefill>
            <use-strict-min>false</use-strict-min>
            <flush-strategy>FailingConnectionOnly</flush-strategy>
        </pool>
         <security>
            <user-name>postgres</user-name>
            <password>postgres</password>
        </security>
        <statement>
            <prepared-statement-cache-size>32</prepared-statement-cache-size>
        </statement>
</datasource>
```

Add this driver to the same file:
```xml
<driver name="org.postgresql" module="org.postgresql">
    <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
    <datasource-class>org.postgresql.Driver</datasource-class>
</driver>
```

