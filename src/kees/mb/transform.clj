(ns kees.mb.transform
  (:require [meander.epsilon :as m]))

(defmulti sanitize :class)

#_{:clj-kondo/ignore [:unresolved-symbol]}
(defmethod sanitize "Image"
  [block]
  (m/match block
    {:class ?class
     :title ?title
     :image {:original {:url ?url}}}
    {:class ?class
     :title ?title
     :image ?url}))

#_{:clj-kondo/ignore [:unresolved-symbol]}
(defmethod sanitize "Media"
  [block]
  (m/match block
    {:class ?class
     :title ?title
     :source {:url ?source}
     :image {:original {:url ?image}}}
    {:class ?class
     :title ?title
     :source ?source
     :image ?image}))

#_{:clj-kondo/ignore [:unresolved-symbol]}
(defmethod sanitize "Link"
  [block]
  (m/match block
    {:class ?class
     :title ?title
     :source {:url ?source}
     :image {:original {:url ?image}}}
    {:class ?class
     :title ?title
     :source ?source
     :image ?image}))

#_{:clj-kondo/ignore [:unresolved-symbol]}
(defmethod sanitize "Text"
  [block]
  (m/match block
    {:class ?class
     :title ?title
     :content_html ?content}
    {:class ?class
     :title ?title
     :text ?content}))

#_{:clj-kondo/ignore [:unresolved-symbol]}
(defmethod sanitize "Attachment"
  [block]
  (m/match block
    {:class ?class
     :title ?title
     :image {:original {:url ?image}}
     :attachment {:url ?source}}
    {:class ?class
     :title ?title
     :image ?image
     :source ?source}))

#_{:clj-kondo/ignore [:unresolved-symbol]}
(defmethod sanitize "Channel"
  [block]
  (m/match block
    {:class ?class
     :title ?title}
    {:class ?class
     :title ?title}))

#_{:clj-kondo/ignore [:unresolved-symbol]}
(defmethod sanitize :default
  [block]
  (m/match block
    {:class ?class
     :title ?title}
    {:class ?class
     :title ?title}))
