(ns myservice.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.params :refer :all]
            [ring.middleware.multipart-params :as mp]
            [clojure.tools.logging :as log]))

(defn init!
  []
  (log/info "init!"))

(defn destroy!
  []
  (log/info "destroy!"))

(defn echo [req]
  (let [results (map
                  (fn [x] {x ((:query-params req) x)})
                  (keys (:query-params req)))]
    (log/infof "req: %s" req)
    (log/infof "results: %s" (pr-str results))
    results))

(defn upload-file
  [file]
  (log/infof "file: %s" file))

(defroutes app-routes
           (GET "/healthz" [] "OK\n")
           (GET "/echo"
                []
             (fn [req] (echo req)))
           (GET "/events/:field_id"
                [field_id :as request]
             (format
               "field_id: %s query_params: %s\n"
               field_id
               (apply str (echo request))))
           (GET "/events/:field_id/:layer"
                [field_id layer :as request]
             (format
               "field_id: %s, layer: %s, query_params: %s\n"
               field_id
               layer
               (apply str (echo request))))
           (GET "/events/:field_id/:layer/:event_date"
                [field_id layer event_date :as request]
             (format
               "field_id: %s, layer: %s, event_date: %s, query_params: %s\n"
               field_id
               layer
               event_date
               (apply str (echo request))))
           ;; Multipart experiments
           (POST "/events/:field_id/:layer/:event_date"
                 [field_id layer event_date meta file preview :as request]
             (log/infof "request: %s" request)
             (log/infof "params: %s" (get request :params))
             (log/infof "field_id: %s" field_id)
             (log/infof "layer: %s" layer)
             (log/infof "event_date: %s" event_date)
             (log/infof "meta: %s" meta)
             (log/infof "file: %s" file)
             (log/infof "preview: %s" preview)
             "OK")
           (POST "/file"
                 {params :params}
             (log/infof "params: %s" params)
             (log/infof "meta: %s" (get params :meta))
             (log/infof "file: %s" (get params :file))
             "OK")
           (route/not-found "Not Found"))

(defroutes standalone-routes
           (context "/myservice" req app-routes)
           (route/not-found "Not Found"))

(def app
  (do
    (log/info "app")
    (mp/wrap-multipart-params (wrap-params (wrap-defaults app-routes api-defaults)))))

(def standalone-app
  (do
    (log/info "standalone-app")
    (mp/wrap-multipart-params (wrap-params (wrap-defaults standalone-routes api-defaults)))))

