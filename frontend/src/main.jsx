import React, { useState } from 'react';
import { createRoot } from 'react-dom/client';
import './styles.css';

const BACKEND_URL = (import.meta.env.VITE_BACKEND_URL
  ?? (import.meta.env.DEV ? 'http://localhost:8080' : window.location.origin))
  .replace(/\/$/, '');
const API_URL = `${import.meta.env.VITE_BACKEND_URL ? BACKEND_URL : ''}/api/v1/urlshortener`;

function CopyIcon() {
  return (
    <svg viewBox="0 0 24 24" aria-hidden="true">
      <rect x="8" y="8" width="11" height="11" rx="1" />
      <path d="M16 8V5H5v11h3" />
    </svg>
  );
}

function App() {
  const [longUrl, setLongUrl] = useState('');
  const [shortUrl, setShortUrl] = useState('');
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [copied, setCopied] = useState(false);

  async function shortenUrl(event) {
    event.preventDefault();
    setError('');
    setCopied(false);

    if (!longUrl.trim()) {
      setError('Please enter a URL.');
      return;
    }

    setIsLoading(true);
    try {
      const response = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'text/plain' },
        body: longUrl.trim(),
      });
      if (!response.ok) throw new Error('Unable to shorten this URL.');

      const shortCode = (await response.text()).trim();
      setShortUrl(`${BACKEND_URL}/api/v1/${shortCode}`);
    } catch (requestError) {
      setError(requestError.message);
    } finally {
      setIsLoading(false);
    }
  }

  async function copyShortUrl() {
    await navigator.clipboard.writeText(shortUrl);
    setCopied(true);
  }

  function startOver() {
    setShortUrl('');
    setCopied(false);
    setError('');
  }

  if (shortUrl) {
    return (
      <main className="app-shell result-page">
        <nav className="nav">
          <span className="brand"><span className="brand-mark" />shorten</span>
          <button className="back-button" type="button" onClick={startOver}>← Shorten another</button>
        </nav>
        <section className="result-hero">
          <div className="eyebrow">YOUR SHORT LINK</div>
          <h1>Your link is ready</h1>
          <p className="subtitle">Share your shorter link anywhere.</p>
          <div className="result-card result-card--page">
            <div>
              <span className="result-label">Shortened URL</span>
              <a href={shortUrl} target="_blank" rel="noreferrer">{shortUrl}</a>
            </div>
            <button className="copy-button" type="button" onClick={copyShortUrl} aria-label="Copy short URL">
              {copied ? 'Copied' : <CopyIcon />}
            </button>
          </div>
          {copied && <p className="copied-message">Link copied to clipboard.</p>}
        </section>
      </main>
    );
  }

  return (
    <main className="app-shell">
      <nav className="nav">
        <span className="brand"><span className="brand-mark" />shorten</span>
        <span className="nav-note">Simple links, instantly.</span>
      </nav>

      <section className="hero">
        <div className="intro-quote">
          <h2>Understand what clicks with your audience</h2>
          <h4>
            URL Shortener makes it easy to create, share, and track short links.
            <br />
            Find what's resonating and scale it into bigger reach, more clicks, and more sales.
          </h4>
        </div>

        <div className="eyebrow">URL SHORTENER</div>
        <h1>Short a long link</h1>
        <p className="subtitle">Short links. Fast sharing. Simple connections.</p>

        <form className="shorten-form" onSubmit={shortenUrl}>
          <input
            aria-label="Long URL"
            type="url"
            value={longUrl}
            onChange={(event) => setLongUrl(event.target.value)}
            placeholder="https://example.com/my-long-url"
          />
          <button type="submit" disabled={isLoading}>
            {isLoading ? 'Shortening...' : 'Get your link for free'}
          </button>
        </form>
        {error && <p className="error">{error}</p>}

      </section>

      <footer>Free to use · No sign up required</footer>
    </main>
  );
}

createRoot(document.getElementById('root')).render(
  <React.StrictMode><App /></React.StrictMode>,
);
