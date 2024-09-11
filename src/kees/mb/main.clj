(ns kees.mb.main
  (:require [kees.mb.data :as data]
            [kees.mb.transform :as transform]))

(comment
  (map transform/sanitize (data/channel-contents {:slug "hardly"})))
