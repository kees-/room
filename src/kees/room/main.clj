(ns kees.room.main
  (:require [kees.room.data :as data]
            [kees.room.transform :as transform]
            [kees.room.util :as util]
            [selmer.parser :as selmer]))

(comment
  (selmer/cache-off!)

  (mapv transform/sanitize (data/channel-contents {:slug "hardly"}))
  
  (->> (data/channel-contents {:slug "van-helsing"})
       (mapv transform/sanitize)
       (util/pretty-spit "target/sample.edn"))
  
  (def sample-image
    (get
     (mapv transform/sanitize (data/channel-contents {:slug "hardly"}))
     4)))

(defn render-page!
  "Write an HTML page based on a given block datum."
  [block target]
  (let [{:keys [class]} block
        template (str "block/" class ".html")]
    (->> (selmer/render-file template block)
         (spit (str "target/" target)))))

(comment
  (render-page! sample-image "sample.html"))
