all: min

repl: webpack
	clojure -C:dev --main rebel-readline.main --repl

min: webpack
	clojure --main "figwheel.main" --build-once "config/min"
	cp target/public/cljs-out/min-main.js dist/app.js
	cp -r resources/public/css dist

show-figwheel-config:
	clojure -m figwheel.main -pc -b dev -r

webpack: target/public/js-out/react.js target/public/js-out/react.min.js

target/public/js-out/react.js: node_modules src/js/**
	yarn webpack --config config/webpack-dev.config.js

target/public/js-out/react.min.js: node_modules src/js/**
	yarn webpack --config config/webpack-prd.config.js

node_modules: package.json
	yarn install
	touch node_modules

clean:
	rm -rf target out nashorn_code_cache .cljs_nashorn_repl
	rm -rf .cpcache .nrepl-port
	rm -rf dist/*.js dist/*.map dist/css

distclean: clean
	rm -rf tmp node_modules
	rm -rf .rebel_readline_history
