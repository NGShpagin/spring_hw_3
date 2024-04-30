FROM postgres:latest
LABEL authors="nikolajspagin"
ENV POSTGRES_DB library
ENV POSTGRES_USER user
ENV POSTGRES_PASSWORD secret