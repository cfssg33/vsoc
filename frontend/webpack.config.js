const HtmlWebpackPlugin = require('html-webpack-plugin');
const VueLoaderPlugin = require('vue-loader/lib/plugin');
const { VuetifyLoaderPlugin } = require('vuetify-loader');
const webpack = require('webpack');

const path = require('path');
function resolve(dir) {
  return path.join(__dirname, '.', dir);
}
module.exports = {
  entry: ['./src/main.js'],
  output: {
    path: path.resolve(__dirname, '../vsoc_mon/src/main/resources/static'),
    filename: 'js/[name].js',
    publicPath: '/'
  },
  optimization: {
    splitChunks: {
      chunks: 'all',
    },
  },
  module: {
    rules: [
      {
        test: /\.m?js$/,
        include: [resolve('/src'), resolve('/node_modules')],
        exclude: [/@babel(?:\/|\\{1,2})runtime|core-js/],
        use: {
          loader: 'babel-loader',
          options: {
            presets: [
              [
                '@babel/preset-env',
                {
                  targets: {
                    browsers : ['> 0.5%', 'ie >= 11']
                  },
                  useBuiltIns: 'entry',
                  corejs: {version: 3, proposals: true}
                }
              ]
            ],
            plugins: ['@babel/plugin-transform-runtime', '@babel/plugin-transform-modules-commonjs']
          }
        },
      },
      {
        test: /\.vue$/,
        loader: 'vue-loader',
      },
      {
        test: /\.s[ac]ss$/i,
        use: ['style-loader', 'css-loader', 'sass-loader'],
      },
      {
        test: /.css$/,
        use: ['style-loader', 'css-loader'],
      },
      {
        test: /\.(woff|woff2|eot|ttf|otf)$/i,
        loader: 'url-loader',
      },
      {
        test: /\.(png|jpe?g|gif)$/i,
        use: [
          {
            loader: 'file-loader',
          },
        ],
      },
    ],
  },
  resolve: {
    alias: {
      vue$: 'vue/dist/vue.esm.js',
      '@': resolve('src'),
    },
    extensions: ['*', '.js', '.vue', '.json'],
  },
  plugins: [
    new HtmlWebpackPlugin({
      // Also generate a test.html
      filename: 'index.html',
      template: 'public/index.html',
      title: 'vSOC',
    }),
    new VueLoaderPlugin(),
    new webpack.ProvidePlugin({
      process: 'process/browser',
    }),
    new VuetifyLoaderPlugin(),
  ],
};
