dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
//    driverClassName = "org.postgresql.Driver"
    username = "A878499_wed"
    password = "Splatt66"
//	dialect = org.hibernate.dialect.PostgreSQLDialect
}
hibernate {
    cache.use_second_level_cache = false
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:mysql://mysql1122.ixwebhosting.com/A878499_wed_plan?autoreconnect=true"
//            url = "jdbc:postgresql://pgsql1101.ixwebhosting.com/A878499_wed_plan"
			properties {
				maxActive = -1
				minEvictableIdleTimeMillis=1800000
				timeBetweenEvictionRunsMillis=1800000
				numTestsPerEvictionRun=3
				testOnBorrow=true
				testWhileIdle=true
				testOnReturn=true
				validationQuery="SELECT now()"
			 }
			
        }
    }
    test {
        dataSource {
            dbCreate = "create"
            url = "jdbc:mysql://mysql1122.ixwebhosting.com/A878499_wed_plan?autoreconnect=true"
            		//url = "jdbc:postgresql://pgsql1101.ixwebhosting.com/A878499_wed_plan"
        }
    }
    production {
        dataSource {
            dbCreate = "create"
            url = "jdbc:mysql://mysql1122.ixwebhosting.com/A878499_wed_plan?autoreconnect=true"
            		//url = "jdbc:postgresql://pgsql1101.ixwebhosting.com/A878499_wed_plan"
            pooled = true
            properties {
               maxActive = -1
               minEvictableIdleTimeMillis=1800000
               timeBetweenEvictionRunsMillis=1800000
               numTestsPerEvictionRun=3
               testOnBorrow=true
               testWhileIdle=true
               testOnReturn=true
               validationQuery="SELECT 1"
            }
        }
    }
}
