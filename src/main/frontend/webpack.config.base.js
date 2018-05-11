const path = require('path');
const webpack = require('webpack');
//const MiniCssExtractPlugin = require("mini-css-extract-plugin")

let baseConfig = {
    dependencies: [
        path.join(__dirname, "./", "node_modules")
    ],
    mode: 'development',
    entry: {
        bundle: path.resolve(__dirname, './src/js/index.ts'),
        //core: path.resolve(__dirname, './src/scss/core.scss'),
        //bootstrap: path.resolve(__dirname, './src/scss/bootstrap.scss'),
        //mdb: path.resolve(__dirname, './src/scss/mdb.scss'),
        //website: path.resolve(__dirname, './src/scss/website.scss'),
    },
    output: {
        filename: 'js/[name].js',
        publicPath: '/assets/js/',
        path: path.resolve(__dirname, '../../../target/classes/static/assets')
    },
    module: {
        rules: [{
            test: /\.js$/,
            loader: 'babel-loader',
            exclude: /node_modules/,
        }, {
            test: /\.esm.js$/,
            loader: 'babel-loader',
            include: [
                path.resolve('node_modules', 'vue/dist'),
            ]
        }, {
            test: /\.scss?$/,
            loaders: ['style-loader', 'css-loader', 'sass-loader'],
        }, {
            test: /\.css?$/,
            loaders: ['style-loader', 'css-loader', 'sass-loader'],
        }, {
            //     test: /\.scss?$/,
            //     use: [
            //         MiniCssExtractPlugin.loader,
            //         {
            //             loader: "css-loader",
            //         },
            //         "sass-loader"
            //     ],
            // }, {
            //     test: /\.css?$/,
            //     loaders: ['style-loader', 'css-loader', 'sass-loader'],
            // }, {
            test: /\.tsx?$/,
            loader: 'ts-loader',
            exclude: /node_modules/,
            options: {
                appendTsSuffixTo: [/\.vue$/],
            }
        }, {
            test: /\.(png|jpg|gif|svg)$/,
            loader: 'file-loader',
            options: {
                name: 'img/[name].[ext]?[hash]'
            }
        }, {
            test: /\.(ttf|eot|woff|woff2)$/,
            loader: "file-loader",
            options: {
                name: "fonts/[name].[ext]",
            },
        }]
    },
    plugins: [
    ],
    resolve: {
        extensions: ['.ts', '.js', '.json'],
        alias: {
            'jquery$': 'jquery/dist/jquery.min.js',
            'bootstrap$': 'bootstrap/dist/js/bootstrap.bundle.js',
            'waves$': 'node-waves/dist/waves.min.js',
            'mdb$': 'mdbootstrap/js/mdb.min.js',
            'clipboard$': 'clipboard/dist/clipboard.min.js',
            'headroom.js$': 'headroom.js/dist/headroom.min.js',
            'lightslider$': 'lightslider/dist/js/lightslider.min.js',
            'web3$': 'web3/dist/web3.min.js',
        }
    },
    devServer: {
        historyApiFallback: true,
        noInfo: true
    },
    performance: {
        hints: false
    },
    devtool: '#eval-source-map',
};


module.exports = baseConfig;