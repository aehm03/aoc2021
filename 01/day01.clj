(ns day01
	(:require [clojure.string :as str]))


(defn get-input []
	(->> "input"
		(slurp)
		(str/split-lines)
		(map #(Integer/parseUnsignedInt %))
		(into [] )))

(def input (get-input))

(defn task1 [list]
	(reduce
		(fn [counter [a b]] 
			(if (< a b) (inc counter) counter))
		0 
		(partition 2 1 list)))

(defn windows [list]
	(partition 3 1 list))

(defn sumofwindow [window]
	(reduce + window))

(println (task1 input))
(println (task1 (map sumofwindow (windows input))))