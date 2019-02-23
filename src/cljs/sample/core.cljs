(ns sample.core
  (:require
    [react]
    [react-dom]))

;; bypass reagent and cut right to react
;; see https://juxt.pro/blog/posts/react-hooks-raw.html
(defn mount
  []
  (react-dom/render
    (react/createElement "div" #js {} "hello from react")
    (js/document.getElementById "app")))

(defonce init (mount))

(comment

  ;;; eval this in editor to check if we're connected properly
  (js/alert "hi from editor via piggieback")
  (react-dom/render)

  )
