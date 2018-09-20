.PHONY: dev prd min show-figwheel-config foreign-libs clean clobber

dev: foreign-libs
	clojure -A:figwheel

dev-once-raw:
	clojure -R:figwheel -m "cljs.main" -co dev.cljs.edn --compile "sample.core"

dev-once-fig:
	clojure -R:figwheel -m "figwheel.main" --build-once "dev"

prd: foreign-libs
	clojure --main "figwheel.main" --build-once "prd"
	cp target/public/cljs-out/prd-main.js dist/app.js
	cp -r resources/public/css dist

min: foreign-libs
	clojure --main "figwheel.main" --build-once "min"
	cp target/public/cljs-out/min-main.js dist/app.js
	cp -r resources/public/css dist

show-figwheel-config:
	clojure -m figwheel.main -pc -b dev -r

foreign-libs:
	make -C npm

clean:
	make -C npm clean
	rm -rf target out nashorn_code_cache .cljs_nashorn_repl
	rm -rf dist/*.js dist/css

clobber: clean
	make -C npm clobber
	rm -rf tmp
