const _ = require("lodash");
const baseConfig = require("./webpack.config.base");
const webpack = require('webpack');
const UglifyJSPlugin = require('uglifyjs-webpack-plugin');

let prodConfig = {
    mode: 'production',
    optimization: {
        minimizer: [
            new UglifyJSPlugin({
                uglifyOptions: {
                    output: {
                        comments: false
                    },
                    sourceMap: true,
                    compress: {
                        booleans: true
                    }
                }
            })
        ]
    },
    plugins: [
        new webpack.LoaderOptionsPlugin({
            minimize: true
        })
    ],
    devtool: '#source-map'
};

module.exports = _.extend({}, baseConfig, prodConfig);
