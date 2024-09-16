(ns kees.mb.main
  (:require [clojure.pprint]
            [kees.mb.data :as data]
            [kees.mb.transform :as transform]
            [kees.mb.util :as util]
            [selmer.parser :as selmer]))

(comment
  (selmer/cache-off!)

  (mapv transform/sanitize (data/channel-contents {:slug "hardly"}))
  
  (->> (data/channel-contents {:slug "hardly"})
       (mapv transform/sanitize)
       (util/pretty-spit "sample.edn"))
  
  (defonce sample-image
    (second
     (mapv transform/sanitize
           (data/channel-contents {:slug "hardly"}))))
  
  (->> (selmer/render-file "block/image.html" sample-image)
       (spit "sample.html")))
