(ns poker.core
  (:gen-class))

(require '[clojure.core.match :refer [match]])

(defrecord Card [suit number])

(def suits [:Spade :Dia :Heart :Club])

(def Deck (for [suit suits number (range 1 13)] (Card. suit number)))

(def GameDeck (vec (shuffle Deck)))

(def rank [:NoPair 
           :OnePair 
           :TwoPair 
           :Tripple
           :Straight 
           :Flush 
           :FullHouse 
           :FourCard 
           :StraightFlush 
           :RoyalStraight
           :RoyalStraightFlush])

(defn pair [hand]
  (let [numbers (->> hand
                     (map #(.number %))
                     frequencies
                     (sort-by #(val %) > )
                     (map val))]
    (match [numbers]
      [([1 & r] :seq   )] :NoPair
      [([2 1 & r] :seq )] :OnePair
      [([2 2 & r] :seq )] :TwoPair
      [([3 1 & r] :seq  )] :Tripple
      [([3 2 & r] :seq )] :FullHouse
      [([4 & r] :seq    )] :FourCard
      ) ))

(defn flush [hand]
  (let [counts (->> hand
                    (map #(.suit %))
                    frequencies
                    first
                    val)]
    (if (= 5 counts) :Flush :NoPair)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
