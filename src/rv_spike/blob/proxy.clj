(ns rv-spike.blob.proxy
  (:require [rv-spike.core :as core]
            [rv-spike.blob.impl :as impl]
            [clojure.string :as str])
  (:gen-class
   :implements [com.amazonaws.services.lambda.runtime.RequestStreamHandler]))

(defn noop [_db _request])

(defn get-uuid [request]
  (-> request
      (get-in ["pathParameters" "proxy"])
      (str/split #"/")
      last))

(defn handler [request]
  (let [path (get-in request ["pathParameters" "proxy"])
        nested? (.contains path "/")
        entity (first (re-seq #"\w+" path))
        method (get request "httpMethod")]
    (case [entity method nested?]
      ["blob" "GET"    true]  (impl/read!   core/db (get-uuid request))
      ["blob" "DELETE" true]  (impl/delete! core/db (get-uuid request))
      ["blob" "GET"    false] (impl/list!   core/db)
      ["blob" "POST"   false] (impl/create! core/db (get request "body"))
      nil)))

(defn -handleRequest [this in out context]
  (core/io-handler handler in out))
