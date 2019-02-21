(ns sample.helpers
  (:require [nrepl.server :as nrepl-server]
            [cider.nrepl]
            [cider.piggieback]
            [clojure.java.io :as io]
            [figwheel.main.api]))

(defonce *running-nrepl-server (atom nil))

(defn make-nrepl-handler
  "manually prepares cider-nrepl-handler with piggieback"
  []
  (let [middleware-symbols (conj cider.nrepl/cider-middleware
                                 'cider.piggieback/wrap-cljs-repl)
        middleware-vars (map resolve middleware-symbols)]
  (apply nrepl-server/default-handler middleware-vars)))

(defn start-nrepl!
  [port]
  (let [server (nrepl-server/start-server
                 :port port
                 :handler (make-nrepl-handler))]
  (reset! *running-nrepl-server server)
  (spit ".nrepl-port" port)
  (str "Cider nREPL server started on port" port)))

(defn stop-nrepl!
  []
  (when (not (nil? @*running-nrepl-server))
    (nrepl-server/stop-server @*running-nrepl-server)
    (reset! *running-nrepl-server nil)
    (io/delete-file ".nrepl-port" true)
    (println "Cider nREPL server on port"
             (:port @*running-nrepl-server) "stopped")))

(defn start-figwheel!
  [build-id]
  (figwheel.main.api/start {:mode :serve} build-id)
  (println (str "Figwheel started with build \"" build-id "\"")))

(defn stop-figwheel!
  [build-id]
  (figwheel.main.api/stop build-id))
