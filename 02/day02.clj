(require '[clojure.string :as str])

(def test-input (list "forward 5" "down 5" "forward 8" "up 3" "down 8" "forward 2"))

(defn get-input []
        (->> "input"
                (slurp)
                (str/split-lines)
								(map (fn [x]  (str/split x #"\s")))
								(map (fn [x] [(first x) (Integer/parseInt (second x))] ))
                (into [] )))

(def input (get-input))


(def groups (group-by first input))

(def up (reduce + (map (fn [x] (second x)) (get groups "up"))))
(def down  (reduce + (map (fn [x] (second x)) (get groups "down"))))
(def fwd (reduce + (map (fn [x] (second x)) (get groups "forward"))))

(println (* fwd (- down up)))

(def task-2
	(reduce
		(fn [res command]
			(cond
				(= (first command) "up") (update res :aim - (second command))
				(= (first command) "down") (update res :aim + (second command))
				(= (first command) "forward") (update 
																				(update res :x + (second command))
																				:y + (* (:aim res) (second command)))))
			{:x 0 :y 0 :aim 0}
			input))
 (println (* (:x task-2) (:y task-2)))