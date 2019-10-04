(defproject rv-spike "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.json "0.2.6"]
                 [funcool/clojure.jdbc "0.9.0"]
                 [org.postgresql/postgresql "42.2.6"]
                 [com.amazonaws/aws-lambda-java-core "1.2.0"]
                 [org.clojure/spec.alpha "0.2.176"]]
  :java-source-paths ["src/java"]
  :aot :all)
