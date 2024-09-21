(ns kees.room.transform
  (:require [clojure.string :as s]
            [meander.epsilon :as m]))

(defmulti
  "Given a complete arena block object with all data, pare its irrelevant keys."
  sanitize :class)

#_{:clj-kondo/ignore [:unresolved-symbol]}
(defmethod sanitize "Image"
  [block]
  (m/match block
    {:class ?class
     :title ?title
     :image {:original {:url ?url}}}
    {:class (s/lower-case ?class)
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
    {:class (s/lower-case ?class)
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
    {:class (s/lower-case ?class)
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
    {:class (s/lower-case ?class)
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
    {:class (s/lower-case ?class)
     :title ?title
     :image ?image
     :source ?source}))

#_{:clj-kondo/ignore [:unresolved-symbol]}
(defmethod sanitize "Channel"
  [block]
  (m/match block
    {:class ?class
     :title ?title
     :slug ?slug
     :owner_slug ?owner_slug}
    {:class (s/lower-case ?class)
     :title ?title
     :url (str "https://are.na/" ?owner_slug "/" ?slug)}))

#_{:clj-kondo/ignore [:unresolved-symbol]}
(defmethod sanitize :default
  [block]
  (m/match block
    {:class ?class
     :title ?title}
    {:class (s/lower-case ?class)
     :title ?title}))
