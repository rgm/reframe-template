(ns user
  (:require [figwheel.main.api]
            [sample.helpers :as help]))

;;; starting up figwheel manually so that we can debug piggiebacking into the
;;; browser from cljs files
;;;
;;; for vim + fireplace.vim:
;;; `:Piggieback (figwheel.main.api/repl-env "config/dev")`
;;;
;;; vscode + calva:
;;; TODO - where's the doc for this?? Must it be `lein?`

(def nrepl-port 7888)

(def figwheel-build-id "config/dev")

(defn go
  []
  (help/start-nrepl! 7888)
  (help/start-figwheel! figwheel-build-id))

(defn stop
  []
  (help/stop-nrepl!)
  (help/stop-figwheel! figwheel-build-id))

(defn cljs-repl
  []
  (figwheel.main.api/cljs-repl figwheel-build-id))

(defn repl-env
  []
  (figwheel.main.api/repl-env figwheel-build-id))

(println "[Sample] Type (go) to start figwheel and nrepl")

(comment

  ;; check nrepl from jvm
  (+ 1 2)

  )
