build:
	docker build -t kevinjqiu/codesearch-api .

push:
	docker push kevinjqiu/codesearch-api

run:
	docker run -d -p 8081:8081 kevinjqiu/codesearch-api

dev:
	gunicorn -b 0.0.0.0:8081 --timeout 65535 app:app
