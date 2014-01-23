dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
//	driverClassName = "org.postgresql.Driver"
//	dialect = org.hibernate.dialect.PostgreSQLDialect
	dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:mysql://fake_stuff?autoreconnect=true"
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
    test {
        dataSource {
            dbCreate = "create"
            url = "jdbc:mysql://fake_stuff?autoreconnect=true"
        }
    }
    production {
        dataSource {
            dbCreate = "create"
            url = "jdbc:mysql://fake_stuff?autoreconnect=true"            
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
