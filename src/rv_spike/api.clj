(ns rv-spike.api
  (:require [clojure.spec.alpha :as s]
            [clojure.set :as set]))

(s/def ::resource-object
  (s/keys :req-un [::id
                   ::type]
          :opt-un [::attributes
                   ::relationships
                   ::links
                   ::meta]))

(s/def ::resource-identifier-object)

(s/def ::resource
  (s/or ::resource-object
        ::resource-identifier-object
        null?))

(s/def ::resource-collection
  (s/or (s/coll-of ::resource-object)
        (s/coll-of ::resource-identifier-object)
        empty?))

(s/def ::error
  (s/keys :opt-un [::id
                   ::links
                   ::status
                   ::code
                   ::title
                   ::detail
                   ::source
                   ::meta]))

(s/def ::data (s/or ::resource ::resource-collection))

(s/def ::meta map?)

(s/def ::link-object
  (s/keys :req-un [::href ::meta]))



(s/def ::links
  (s/keys :opt-un [::self ::related ::pagination]))

(s/def ::version string?)

(s/def ::jsonapi
  (s/keys :opt-un [::version
                   ::meta]))

(defn one-of-keys [m xs]
  (let [ks (set (keys m))]
    (some #(contains? ks %) xs)))

(s/def ::top-level
  (s/and #(one-of-keys % [:data :errors :meta])
         #(not (when-not (contains? % :data) (contains? % :included)))
         (s/keys :opt-un [(s/or ::data
                                ::errors)
                          ::meta
                          ::jsonapi
                          ::links
                          ::included])))
