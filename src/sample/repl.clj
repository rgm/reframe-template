(ns sample.repl
  (:require [clojure.java.io :as io]
            [cider.nrepl]
            [figwheel.main.api :as figwheel]
            [nrepl.server :as nrepl]))


(def nrepl-port 7888)

(defonce nrepl-server (atom nil))

(defn start-nrepl-server!
  []
  (reset! nrepl-server
          (nrepl/start-server :port nrepl-port
                              :handler cider.nrepl/cider-nrepl-handler))
  (println "Cider nREPL server started on port" nrepl-port)
  (spit ".nrepl-port" nrepl-port))

(defn stop-nrepl-server!
  []
  (when-not (nil? @nrepl-server)
    (nrepl/stop-server @nrepl-server)
    (println "Cider nREPL server on port" nrepl-port "stopped")
    (reset! nrepl-server nil)
    (io/delete-file ".nrepl-port" true)))

(def figwheel-config
  {:log-file "tmp/figwheel-main.log"
   :mode :serve
   :open-url false
   :css-dirs ["resources/public/css"]
   ;; this can substitute for foreign-libs
   ;; figwheel can infer this, at least for now
   :npm {:bundles
         {"npm/dist/react.js" "npm/src/react.js"
          "npm/dist/semantic_ui.js" "npm/src/semantic_ui.js"
          "npm/dist/leaflet.js" "npm/src/leaflet.js"
          "npm/dist/vega.js" "npm/src/vega.js"
          "npm/dist/reframe10x.js" "npm/src/reframe10x.js"}}})

(def compiler-options
  {:main 'sample.core
   :watch-dirs ["src"]
   ; :foreign-libs foreign-libs
   ; :output-to "target/public/cljs-out/dev-main.js"
   ; :output-dir "target/public/cljs-out/dev"
   ; :output-to "out/dev-main.js"
   ; :output-dir "out"
   ; :target-dir "out"
   ; :asset-path "/"
   :infer-externs true
   :npm-deps false})

(defn start-figwheel!
  []
  (figwheel/start {:id "dev"
                   :options compiler-options
                   :config figwheel-config}))

(defn start-cljs-repl
  []
  ;; TODO as far as I can tell this is what does the piggieback wrapping
  (figwheel/cljs-repl "dev"))

(defn stop-figwheel!
  []
  (figwheel/stop "dev"))

(comment
  (start-figwheel!)
  (stop-figwheel!)
  (figwheel.main/reset))


; (require '[cljs.repl :as repl]
;          '[cljs.repl.browser :as browser])
;
; (def foreign-libs
;   [{:file "npm/dist/leaflet.js"
;     :provides ["leaflet" "leaflet-draw"]
;     :global-exports '{leaflet Leaflet leaflet-draw LeafletDraw}}
;    {:file "npm/dist/vega.js"
;     :provides ["vega"]
;     :global-exports '{vega Vega}}
;    {:file "npm/dist/semantic_ui.js"
;     :provides ["semantic-ui"]
;     :global-exports '{semantic-ui SemanticUI}}
;    {:file "npm/dist/react.js"
;     :provides ["react"
;                "react-dom"
;                "react-dom-server"
;                "create-react-class"
;                "react-transition-group"]
;     :global-exports '{react React
;                       react-dom ReactDOM
;                       react-dom-server ReactDOMServer
;                       create-react-class createReactClass
;                       react-transition-group RTG}}
;    {:file "npm/dist/reframe10x.js"
;     :provides ["cljsjs.react-highlight"
;                "cljsjs.highlight.langs.clojure"
;                "cljsjs.react-flip-move"]
;     :global-exports '{cljsjs.react-highlight Highlight
;                       cljsjs.highlight.langs.clojure HighlightLangsClojure
;                       cljsjs.react-flip-move FlipMove}}])

; (cljs.repl/repl (browser/repl-env)
;                 :foreign-libs foreign-libs)

; https://github.com/bhauman/figwheel-main/blob/c93322cc3d7c41bf333e4e2f0718b9c6ca71593b/docs/docs/vim.md
; (figwheel.main.api/cljs-repl "dev") ;; need something for foreign libs

; to get things going in vim
; :Piggieback (figwheel.main.api/repl-env "dev")
