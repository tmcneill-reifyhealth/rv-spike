(ns rv-spike.core
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]
            [clojure.pprint :as pp]))

(def db (atom {:blob []}))

(defn response [raw]
  {"statusCode" 200,
   "headers" {},
   "body" (json/write-str raw)})

(defn spy [x]
  (pp/pprint x)
  x)

(defn decode-body [j]
  (if (get j "body")
    (update-in j ["body"] json/read-str)
    j))

(defn io-handler [handler in out]
  (let [w (io/writer out)]
    (-> (json/read (io/reader in))
        decode-body
        spy
        handler
        response
        (json/write w))
    (.flush w)))
