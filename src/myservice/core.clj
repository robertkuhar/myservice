(ns myservice.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.params :refer :all]
            [clojure.tools.logging :as log]))

(defn init!
  []
  (println "println init!")
  (log/info "init!"))

(defn destroy!
  []
  (println "println destroy!")
  (log/info "destroy!"))

(defn echo [req]
  (let [results (map
                  (fn [x] { x ((:query-params req) x)})
                  (keys (:query-params req)))]
    (log/info results)
    results))

(defroutes app-routes
           (GET "/healthz" [] "OK")
           (GET "/echo" [] (fn [req]
                             (echo req)))
           (GET "/events/:field_id"
                [field_id :as request]
             (format
               "field_id: %s query_params: %s"
               field_id
               (apply str (echo request))))
           (GET "/events/:field_id/:layer"
                [field_id layer :as request]
             (format
               "field_id: %s, layer: %s, query_params: %s"
               field_id
               layer
               (apply str (echo request))))
           (GET "/events/:field_id/:layer/:event_date"
                [field_id layer event_date :as request]
             (format
               "field_id: %s, layer: %s, event_date: %s, query_params: %s"
               field_id
               layer
               event_date
               (apply str (echo request))))
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

