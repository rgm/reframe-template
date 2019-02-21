var path = require("path");

module.exports = {
  entry: {
    react: "./src/js/react.js"
  },
  mode: "development",
  devtool: "source-map",
  output: {
    path: path.resolve(path.join(__dirname, "../target/public/js-out")),
    filename: "[name].js"
  }
};
