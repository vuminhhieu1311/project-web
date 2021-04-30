const path = require('path');
const webpack = require('webpack');
const {merge} = require('webpack-merge');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const utils = require('./utils.js');

module.exports = (options) => {
    return merge(
        // jhipster-needle-add-webpack-config - JHipster will add custom config
        {
            cache: options.env !== 'production',
            resolve: {
                extensions: ['.js', '.json'],
                modules: ['node_modules'],
                fallback: {
                    path: require.resolve('path-browserify'),
                },
            },
            module: {
                rules: [
                    {
                        test: /\.m?js$/,
                        exclude: /(node_modules|bower_components)/,
                        use: {
                            loader: 'babel-loader',
                            options: {
                                presets: [
                                    {
                                        plugins: [
                                            '@babel/plugin-proposal-class-properties'
                                        ]
                                    }
                                ]
                            }
                        }
                    },
                    {
                        test: /\.(jpe?g|png|gif|svg|woff2?|ttf|eot)$/i,
                        loader: 'file-loader',
                        options: {
                            digest: 'hex',
                            hash: 'sha512',
                            name: 'content/[hash].[ext]',
                        },
                    },
                ],
            },
            stats: {
                children: false,
            },
            optimization: {
                splitChunks: {
                    cacheGroups: {
                        commons: {
                            test: /[\\/]node_modules[\\/]/,
                            name: 'vendors',
                            chunks: 'all',
                        },
                    },
                },
            },
            plugins: [
                new webpack.DefinePlugin({
                    'process.env': {
                        NODE_ENV: `'${options.env}'`,
                        // APP_VERSION is passed as an environment variable from the Gradle / Maven build tasks.
                        VERSION: `'${process.env.hasOwnProperty('APP_VERSION') ? process.env.APP_VERSION : 'DEV'}'`,
                        DEBUG_INFO_ENABLED: options.env === 'development',
                        // The root URL for API calls, ending with a '/' - for example: `"https://www.jhipster.tech:8081/myservice/"`.
                        // If this URL is left empty (""), then it will be relative to the current context.
                        // If you use an API server, in `prod` mode, you will need to enable CORS
                        // (see the `jhipster.cors` common JHipster property in the `application-*.yml` configurations)
                        SERVER_API_URL: `''`,
                    },
                }),
                new CopyWebpackPlugin({
                    patterns: [
                        {
                            context: './node_modules/swagger-ui-dist/',
                            from: '*.{js,css,html,png}',
                            to: 'swagger-ui/',
                            globOptions: {ignore: ['**/index.html']},
                        },
                        {from: './src/main/webapp/swagger-ui/', to: 'swagger-ui/'},
                        {from: './src/main/webapp/content/', to: 'content/'},
                        {from: './src/main/webapp/favicon.ico', to: 'favicon.ico'},
                        {from: './src/main/webapp/manifest.webapp', to: 'manifest.webapp'},
                        // jhipster-needle-add-assets-to-webpack - JHipster will add/remove third-party resources in this array
                        {from: './src/main/webapp/robots.txt', to: 'robots.txt'},
                        {from: './src/main/webapp/template/', to: './'}
                    ],
                }),
                new HtmlWebpackPlugin({
                    template: './src/main/webapp/index.html',
                    chunksSortMode: 'auto',
                    inject: 'body',
                    base: '/',
                }),
            ],
        }
    );
};
