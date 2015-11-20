(defproject myservice "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [org.clojure/tools.logging "0.3.1"]
                 [clj-http "2.0.0"]]

  :plugins [[lein-ring "0.9.7"]]

  :ring {:handler myservice.core/standalone-app
         :port 3000}
  :profiles {
             :uberjar {:ring {:handler myservice.core/app}}}
  )
