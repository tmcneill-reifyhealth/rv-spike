(ns rv-spike.blob.actions
  (:require [rv-spike.core :as core]
            [rv-spike.blob.impl :as impl])
  (:import com.amazonaws.services.lambda.runtime.RequestStreamHandler))

(gen-class
 :name "rv_spike.blob.actions.create"
 :prefix "create-"
 :implements [com.amazonaws.services.lambda.runtime.RequestStreamHandler])

(defn create-handleRequest [this in out context])

(gen-class
 :name "rv_spike.blob.actions.print"
 :prefix "print-"
 :implements [com.amazonaws.services.lambda.runtime.RequestStreamHandler])

(defn print-handleRequest [this in out context]
  (core/io-handler identity in out))
