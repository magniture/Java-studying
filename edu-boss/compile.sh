#!/bin/bash
## 线上编译脚本（运维新定义的上线规范）

cd `dirname $0`
export   PATH=/apps/nvm/versions/node/v10.16.0/bin/:$PATH
npm set registry https://registry.npm.taobao.org/
 rm -rf dist
# rm -rf node_modules

npm i
npm run build
mkdir dist/static
cp -r ./dist/assets ./dist/static/assets
cp -r ./dist/favicon.ico ./dist/static/favicon.ico
if [ $? -eq 0 ];then
    echo "编译成功!!!"
else
    echo "编译失败!!!"
    exit 2
fi
