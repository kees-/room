(ns kees.mb.data
  (:require [babashka.http-client :as http]
            [cheshire.core :as json]
            [kees.mb.util :as util]))

(def api-base "https://api.are.na/v2")
(def per 100)

(defn form-url
  [& paths]
  (reduce str (interpose \/ (vec paths))))

(defn is-channel-valid?
  [slug]
  (let [req (form-url api-base "channels" slug)
        opts {:headers {"Accept" "application/json"}
              :query-params {:per 0}
              :throw false}
        res (http/get req opts)]
    (-> res :status (= 200))))

(defn channel-length
  [slug]
  (let [req (form-url api-base "channels" slug)
        opts {:headers {"Accept" "application/json"}
              :query-params {:per 0}}
        res (http/get req opts)]
    (-> res :body (json/decode keyword) :length)))

(defn- channel-contents-page-requester
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
  [{:keys [slug per]}]
  (let [len (channel-length slug)
        pages (util/ceil (/ len per))
        requester (channel-contents-page-requester {:slug slug :per per})
        data (into [] (map requester) (range 1 (inc pages)))]
    (reduce into [] data)))

(comment
  (is-channel-valid? "villainy")
  (is-channel-valid? "villazziny")
  (channel-length "villainy")
  (channel-contents {:slug "hardly" :per 5}))
