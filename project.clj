(defproject myservice "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [metosin/compojure-api "1.0.1"]
                 [ring/ring-defaults "0.1.5"]
                 [org.clojure/tools.logging "0.3.1"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [clj-http "2.0.0"]]

  :plugins [[lein-ring "0.9.7"]]

  :ring {:port 3000
         :handler myservice.core/standalone-app
         :init myservice.core/init!
         :destroy myservice.core/destroy!}

  :profiles { :uberjar {:ring {:handler myservice.core/app}}})
