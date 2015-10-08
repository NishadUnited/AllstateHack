environments {
    local {
        sonar {
            hostUrl = 'http://localhost:9000'
            jdbcUrl = 'jdbc:h2:tcp://localhost:9092/sonar'
        }
    }
}