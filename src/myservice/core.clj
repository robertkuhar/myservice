(ns myservice.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.params :refer :all]
            [clojure.tools.logging :as log]))

(defn echo [req]
  (log/info "ROBERT!" req))

(defroutes app-routes
           (GET "/healthz" [] "OK")
           (GET "/echo" [] (fn [req]
                             (echo req)
                             "Do something"))
           (route/not-found "Not Found"))

(defroutes standalone-routes
           (context "/myservice" req app-routes)
           (route/not-found "Not Found"))

(def app
  (do
    (log/info "app")
    (wrap-params (wrap-defaults app-routes site-defaults))))

(def standalone-app
  (do
    (log/info "standalone-app")
    (wrap-params (wrap-defaults standalone-routes site-defaults))))

