(ns kees.room.data
  (:require [babashka.http-client :as http]
            [cheshire.core :as json]
            [kees.room.util :as util]))

(def ^:private api-base "https://api.are.na/v2")
(def ^:private per 100)

(defn- form-url
  "Add slashes between given paths."
  [& paths]
  (reduce str (interpose \/ (vec paths))))

(defn- is-channel-valid?
  "Truthy if HTML response for a channel is 200."
  [slug]
  (let [req (form-url api-base "channels" slug)
        opts {:headers {"Accept" "application/json"}
              :query-params {:per 0}
              :throw false}
        res (http/get req opts)]
    (-> res :status (= 200))))

(defn- channel-length
  "Return number of blocks in a channel."
  [slug]
  (let [req (form-url api-base "channels" slug)
        opts {:headers {"Accept" "application/json"}
              :query-params {:per 0}}
        res (http/get req opts)]
    (-> res :body (json/decode keyword) :length)))

(defn- channel-contents-requester
  "Retrieve a page's worth of blocks from a channel"
  [{:keys [slug per]}]
  (fn [page]
    (let [req (form-url api-base "channels" slug "contents")
          opts {:headers {"Accept" "application/json"}
                :query-params {:page page
                               :per per
                               :sort "position"
                               :direction "asc"}}
          res (http/get req opts)]
      (-> res :body (json/decode keyword) :contents))))

(defn channel-contents
  "Get the full contents of an arena channel."
  [{:keys [slug per] :or {per per}}]
  (let [len (channel-length slug)
        pages (util/ceil (/ len per))
        requester (channel-contents-requester {:slug slug :per per})
        data (into [] (map requester) (range 1 (inc pages)))]
    (reduce into [] data)))
