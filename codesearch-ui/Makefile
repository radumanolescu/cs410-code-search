build:
	yarn build

clean:
	sudo rm -fr /var/www/html

deploy: build clean
	sudo cp -r dist /var/www/html
