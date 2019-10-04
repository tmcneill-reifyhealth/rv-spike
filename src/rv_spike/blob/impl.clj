(ns rv-spike.blob.impl
  (:require [jdbc.core :as jdbc]))

(def dbspec "postgresql://postgres:rv-spike-2@rv-spike-2.cluster-cpxrjwqq5w4o.us-east-1.rds.amazonaws.com:5432/postgres")

(defn create! [db raw]
  (let [{:keys [raw owner_id id] :as blob}
        {:raw raw
         :owner_id #uuid "add59b16-e608-11e9-a276-1223975952f8"
         :id (java.util.UUID/randomUUID)}
        insert ["insert into \"Blob\" (id, raw, owner_id) values (?, ?, ?);"
                id raw owner_id]]
    (jdbc/execute db insert)
    {:data blob}))

(defn list! [db]
  (let [list ["select id, raw, owner_id from \"Blob\";"]
        blobs (jdbc/fetch db list)]
    {:data blobs}))

(defn delete! [db id]
  (let [delete ["delete from \"Blob\" where id = ?" id]]
    (jdbc/execute db delete)
    {:data id}))

(defn read! [db id]
  (let [read ["select id, raw, owner_id from \"Blob\" where id = ?;" id]
        blobs (jdbc/fetch db read)]
    {:data (first blobs)}))
