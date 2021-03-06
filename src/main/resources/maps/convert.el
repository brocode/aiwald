(defun aiwald-to-ascii ()
    "Converts to ascii"
    (interactive)
    (progn
      (goto-char (point-min))
      (while (search-forward "💰" nil t)
        (replace-match "C"))
      (goto-char (point-min))
      (while (search-forward "🌲" nil t)
        (replace-match "W"))
      (goto-char (point-min))
      (while (search-forward "🙋" nil t)
        (replace-match "S"))
      (goto-char (point-min))
      (while (search-forward "⚔" nil t)
        (replace-match "T"))
      (goto-char (point-min))
      (while (search-forward "⾀" nil t)
        (replace-match "B"))
      (goto-char (point-min))
      (while (search-forward "🌻" nil t)
        (replace-match " "))))

(defun aiwald-to-unicode ()
    "Converts to ascii"
    (interactive)
    (progn
      (goto-char (point-min))
      (while (search-forward "C" nil t)
        (replace-match "💰"))
      (goto-char (point-min))
      (while (search-forward "W" nil t)
        (replace-match "🌲"))
      (goto-char (point-min))
      (while (search-forward "S" nil t)
        (replace-match "🙋"))
      (goto-char (point-min))
      (while (search-forward "T" nil t)
        (replace-match "⚔"))
      (goto-char (point-min))
      (while (search-forward "B" nil t)
        (replace-match "⾀"))
      (goto-char (point-min))
      (while (search-forward " " nil t)
        (replace-match "🌻"))))
