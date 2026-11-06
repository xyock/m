const BRIDGE = "http://127.0.0.1:8765";
const XYOCK_URL = "https://xyock.github.io/beta-xyock/";

type HealthResponse = { status: "ok"; service: "slice-connector"; version: string };

const state = document.querySelector<HTMLElement>("#connection-state");
const bridgeStatus = document.querySelector<HTMLElement>("#bridge-status");
const launchButton = document.querySelector<HTMLButtonElement>("#launch-button");
const message = document.querySelector<HTMLElement>("#message");

function showMessage(text: string): void {
  if (message) message.textContent = text;
}

async function checkBridge(): Promise<boolean> {
  try {
    const response = await fetch(`${BRIDGE}/health`, {
      method: "GET",
      headers: { Accept: "application/json" },
      signal: AbortSignal.timeout(2500)
    });
    if (!response.ok) throw new Error(`HTTP ${response.status}`);
    const data = (await response.json()) as HealthResponse;
    if (data.status !== "ok") throw new Error("Unexpected bridge response");
    if (state) state.textContent = "로컬 브리지 연결됨";
    if (bridgeStatus) bridgeStatus.textContent = `정상 · v${data.version}`;
    if (launchButton) launchButton.disabled = false;
    showMessage("로컬 연결이 준비됐습니다.");
    return true;
  } catch {
    if (state) state.textContent = "브리지 연결 안 됨";
    if (bridgeStatus) bridgeStatus.textContent = "실행되지 않음";
    if (launchButton) launchButton.disabled = true;
    showMessage("Java 브리지를 실행한 뒤 다시 확인해 주세요.");
    return false;
  }
}

document.querySelector("#connect-button")?.addEventListener("click", () => {
  void checkBridge();
});

launchButton?.addEventListener("click", () => {
  // Open a fixed, trusted destination. Never accept arbitrary URLs from Entry messages.
  window.open(XYOCK_URL, "_blank", "noopener,noreferrer");
});

void checkBridge();

// Entry integration will be implemented only after confirming Entry's supported
// extension protocol. Incoming messages must be schema-checked and allowlisted.
