const webpack = require('webpack');
const webpackMerge = require('webpack-merge').merge;
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const WorkboxPlugin = require('workbox-webpack-plugin');
const OptimizeCSSAssetsPlugin = require('optimize-css-assets-webpack-plugin');

const utils = require('./utils.js');
const commonConfig = require('./webpack.common.js');

const ENV = 'production';

module.exports = webpackMerge(commonConfig({env: ENV}), {
    // devtool: 'source-map', // Enable source maps. Please note that this will slow down the build
    mode: ENV,
    entry: {
        main: './src/main/webapp/app/index',
    },
    output: {
        path: utils.root('build/resources/main/static/'),
        filename: 'app/[name].[hash].bundle.js',
        chunkFilename: 'app/[name].[hash].chunk.js',
    },
    module: {
        rules: [
            {
                enforce: 'pre',
                test: /\.css$/,
                loader: 'stripcomment-loader',
            },
            {
                test: /\.css$/,
                use: [
                    {
                        loader: MiniCssExtractPlugin.loader,
                        options: {
                            publicPath: '../',
                        },
                    },
                    'css-loader',
                    'postcss-loader'
                ],
            },
        ],
    },
    optimization: {
        runtimeChunk: false,
        minimizer: [
            new OptimizeCSSAssetsPlugin({}),
        ],
    },
    plugins: [
        new MiniCssExtractPlugin({
            // Options similar to the same options in webpackOptions.output
            filename: 'content/[name].[hash].css',
            chunkFilename: 'content/[name].[hash].css',
        }),
        new webpack.LoaderOptionsPlugin({
            minimize: true,
            debug: false,
        }),
        new WorkboxPlugin.GenerateSW({
            clientsClaim: true,
            skipWaiting: true,
            exclude: [/swagger-ui/],
        }),
    ],
});
