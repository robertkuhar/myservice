(ns myservice.core-test
  (:require [clojure.test :refer :all]
            [myservice.core :as service]
            [ring.adapter.jetty :as jetty]
            [clojure.tools.logging :as log]
            [clj-http.client :as client]))

(defn make-service-url
  [port path]
  (let [service-url (format "http://localhost:%d/myservice/%s" port path)]
    (log/info service-url)
    service-url))

(deftest a-test
  (testing "Placeholder"
    (is (= 1 1))))

(deftest endpoints
  (let [jetty-options {:port 0 :join? false}
        server (jetty/run-jetty service/standalone-app jetty-options)
        _ (service/init!)
        _ (.start server)
        port (.getLocalPort (first (.getConnectors server)))
        make-url (partial make-service-url port)]

    (testing "get healthz"
      (let [response (client/get (make-url "healthz") {:throw-exceptions false})
            {:keys [status body]} response]
        (is (= status 200))))

    (testing "get echo"
      (let [query-params {"one" "Uno" "two" "Dos"}
            response (client/get (make-url "echo") {:query-params query-params
                                                    :throw-exceptions false})
            {:keys [status body]} response]
        (is (= status 200))
        (doseq [x (flatten (seq query-params))]
          (let [pattern (re-pattern x)]
            (is (not (nil? (re-find pattern (:body response)))))))))

    (.stop server)))
