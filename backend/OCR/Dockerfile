# 기본 이미지 설정
FROM python:3.9

# 작업 디렉토리 설정
WORKDIR /app

# COPY /var/jenkins_home/workspace/back-ocr/submodule/secrets.json /app/secrets.json

# 의존성 파일 복사 및 설치
COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt
RUN pip install transformers
# 시스템 패키지 업데이트 및 OpenGL 관련 라이브러리 설치
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    libgl1-mesa-glx && \
    rm -rf /var/lib/apt/lists/*


# 애플리케이션 코드 복사
COPY src/ ./src/

# 애플리케이션 코드의 실행 권한 추가
RUN chmod +x src/main.py


# Uvicorn 서버 실행 명령
CMD ["uvicorn", "src.main:api", "--host=0.0.0.0", "--port=8000"]
