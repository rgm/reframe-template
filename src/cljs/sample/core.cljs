(ns sample.core
  (:require [reagent.core :as reagent]))

(defn Layout
  []
  [:<>
   [:h1 "reagent example"]
   [:div
    [:button
     {:on-click #(js/alert "click!")}
     "click me"]] ])

(defn mount-root
  []
  (let [host-node (.getElementById js/document "app")]
    (reagent/render [Layout] host-node)))

(mount-root)

(comment

  ;;; eval this in editor to check if we're connected properly
  (js/alert "hi from editor via piggieback")

  )
