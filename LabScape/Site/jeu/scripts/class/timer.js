class Timer {
    constructor() {
        this.time = 0;
        this.lastUpdateTime = 0;

        this.timer = document.createElement('div');
        this.timer.style.position = 'fixed';
        this.timer.style.top = '10%';

        this.timerText = document.createElement('div');
        this.timerText.style.color = 'red';
        this.timerText.style.fontSize = '30px';
        this.timerText.style.textAlign = 'center';
        this.timerText.style.fontFamily = 'sans-serif';
        this.timerText.style.fontWeight = 'bold';
        this.timerText.style.width = '100%';
        this.timerText.style.height = '100%';
        this.timerText.style.display = 'flex';
        this.timerText.style.alignItems = 'center';
        this.timerText.style.justifyContent = 'center';
        this.timerText.style.zIndex = '1000';

        this.timer.appendChild(this.timerText);
        document.body.appendChild(this.timer);

        this.lastUpdateTime = performance.now();
        this.updateTimer();
    }

    updateTimer() {
        const currentTime = performance.now();
        const deltaTime = (currentTime - this.lastUpdateTime) / 1000;
        this.lastUpdateTime = currentTime;

        this.time += deltaTime;
        this.timerText.textContent = this.formatTime(Math.floor(this.time));

        requestAnimationFrame(() => this.updateTimer());
    }

    formatTime(totalSeconds) {
        const minutes = Math.floor(totalSeconds / 60);
        const seconds = totalSeconds % 60;
        return `${String(minutes)} minutes ${String(seconds)} secondes`;
    }
}

// Exporter la classe
export { Timer };
