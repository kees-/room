(ns kees.room.main
  (:require [clojure.pprint]
            [kees.room.data :as data]
            [kees.room.transform :as transform]
            [kees.room.util :as util]
            [selmer.parser :as selmer]))

(comment
  (selmer/cache-off!)

  (mapv transform/sanitize (data/channel-contents {:slug "hardly"}))
  
  (->> (data/channel-contents {:slug "van-helsing"})
       (mapv transform/sanitize)
       (util/pretty-spit "target/sample.edn"))
  
  (defonce sample-image
    (second
     (mapv transform/sanitize
           (data/channel-contents {:slug "hardly"}))))
  
  (->> (selmer/render-file "block/image.html" sample-image)
       (spit "target/sample.html")))
