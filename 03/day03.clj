(require '[clojure.string :as str])

(defn get-input [path]
		(->> path
				(slurp)
				(str/split-lines)
				(map sequence)
				(flatten) ;; flatten/partitio is either a very elegant or awkward workaround 
				(map  #(int  %)) 
				(map #(- 48 %))  ;; thats also not very nice
				(map #(* -1 %))
				(partition 5 )  ;; unfortunately not equal for test and real input
				(into []))) ;; what does this?

(def test-input (get-input "test-input"))
(def input (get-input "input"))

(defn sum-len [bit-list] 
	(reduce
		(fn [[sums len] bits]
			[ (mapv + sums bits) (inc len)]
		)
		[ [0 0 0 0 0] 0]
		bit-list))

(defn divide-by [sum-len]
	(map #(/ % (second sum-len)) (first sum-len)))

(defn round-all [fracs]
	(map #(int (Math/round (double %))) fracs ))

(defn invert [bits]
	(map #(* -1 (- % 1)) bits))

(defn to-binary [bits]
	(first 
	(reduce 
		(fn [[sum pos] bit]
			(list (+ sum (* pos bit)) (* 2 pos)))
			(list 0 1)
			(reverse bits))))

(defn mult-inv [bits]
	(* (to-binary bits) (to-binary (invert bits))))

(defn filter-pos [pos numberlist template-gen]
	
	(if (= 1 (count numberlist)) 
		numberlist
		(filter-pos
			(inc pos)
			(filterv #( = (nth % pos) (nth (template-gen numberlist) pos)) numberlist) 
			template-gen
		)
	)	
)

(defn task-1 [input]
			(->> input
					(sum-len)
					(divide-by)
					(round-all)
					(mult-inv)
))

(println (task-1 test-input))

(defn most [numbers]
		(->> numbers
				(sum-len)
				(divide-by)
				;(round-all)
		)
)

(def oxy 
		(->> (filter-pos 0 test-input most)
				(first)
				(to-binary)
		)
)

(def co2 
		(->> (filter-pos 0 test-input (invert most))
				(first)
				(to-binary)
		)
)


(println oxy)
(println co2)