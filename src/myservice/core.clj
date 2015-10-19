(ns myservice.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.tools.logging :as log]))

(defroutes app-routes
           (GET "/healthz" [] "OK")
           (route/not-found "Not Found"))

(defroutes standalone-routes
           (context "/myservice" req app-routes)
           (route/not-found "Not Found"))

(def app
  (do
    (log/info "app")
    (wrap-defaults app-routes site-defaults)))

(def standalone-app
  (do
    (log/info "standalone-app")
    (wrap-defaults standalone-routes site-defaults)))