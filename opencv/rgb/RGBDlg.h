
// RGBDlg.h : header file
//
#include <opencv2/opencv.hpp>
#pragma once


// CRGBDlg dialog
class CRGBDlg : public CDialogEx
{
// Construction
public:
	CRGBDlg(CWnd* pParent = nullptr);	// standard constructor

// Dialog Data
#ifdef AFX_DESIGN_TIME
	enum { IDD = IDD_RGB_DIALOG };
#endif
	cv::Mat originalMat;
	cv::Mat channels[3];
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support


// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	DECLARE_MESSAGE_MAP()
public:
	CStatic pictureControl;
	afx_msg void OnBnClickedOpenbtn();
	afx_msg void OnBnClickedAllchannelbtn();
	afx_msg void OnBnClickedRchannelbtn();
	afx_msg void OnBnClickedGchannelbtn();
	afx_msg void OnBnClickedBchannelbtn();
	void DisplayImage(cv::Mat targetMat, int channel);
	void SplitChannels();
};
