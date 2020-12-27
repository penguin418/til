
// RGBDlg.cpp : implementation file
//

#include "pch.h"
#include "framework.h"
#include "RGB.h"
#include "RGBDlg.h"
#include "afxdialogex.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CAboutDlg dialog used for App About

class CAboutDlg : public CDialogEx
{
public:
	CAboutDlg();

// Dialog Data
#ifdef AFX_DESIGN_TIME
	enum { IDD = IDD_ABOUTBOX };
#endif

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

// Implementation
protected:
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialogEx(IDD_ABOUTBOX)
{
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialogEx)
END_MESSAGE_MAP()


// CRGBDlg dialog



CRGBDlg::CRGBDlg(CWnd* pParent /*=nullptr*/)
	: CDialogEx(IDD_RGB_DIALOG, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CRGBDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, ID_Picture, pictureControl);
}

BEGIN_MESSAGE_MAP(CRGBDlg, CDialogEx)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(ID_OpenBtn, &CRGBDlg::OnBnClickedOpenbtn)
	ON_BN_CLICKED(ID_AllChannelBtn, &CRGBDlg::OnBnClickedAllchannelbtn)
	ON_BN_CLICKED(ID_RChannelBtn, &CRGBDlg::OnBnClickedRchannelbtn)
	ON_BN_CLICKED(ID_GChannelBtn, &CRGBDlg::OnBnClickedGchannelbtn)
	ON_BN_CLICKED(ID_BChannelBtn, &CRGBDlg::OnBnClickedBchannelbtn)
END_MESSAGE_MAP()


// CRGBDlg message handlers

BOOL CRGBDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// Add "About..." menu item to system menu.

	// IDM_ABOUTBOX must be in the system command range.
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != nullptr)
	{
		BOOL bNameValid;
		CString strAboutMenu;
		bNameValid = strAboutMenu.LoadString(IDS_ABOUTBOX);
		ASSERT(bNameValid);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon

	// TODO: Add extra initialization here

	return TRUE;  // return TRUE  unless you set the focus to a control
}

void CRGBDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		CAboutDlg dlgAbout;
		dlgAbout.DoModal();
	}
	else
	{
		CDialogEx::OnSysCommand(nID, lParam);
	}
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CRGBDlg::OnPaint()
{
	if (IsIconic())
	{
		CPaintDC dc(this); // device context for painting

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// Center icon in client rectangle
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// Draw the icon
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialogEx::OnPaint();
	}
}

// The system calls this function to obtain the cursor to display while the user drags
//  the minimized window.
HCURSOR CRGBDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}



void CRGBDlg::OnBnClickedOpenbtn()
{
	// 파일 다이얼로그
	static TCHAR BASED_CODE szFilter[] = _T("이미지 파일(*.BMP, *.GIF, *.JPG) | *.BMP;*.GIF;*.JPG;*.bmp;*.jpg;*.gif |모든파일(*.*)|*.*||");
	CFileDialog dlg(TRUE, _T("*.jpg"), _T("image"), OFN_HIDEREADONLY, szFilter);
	if (IDOK == dlg.DoModal())
	{
		CString pathName = dlg.GetPathName();
		std::string strPath(CT2CA(pathName.operator LPCWSTR()));

		// 행렬 변환
		originalMat = cv::imread(strPath);

		// 채널 분해
	    // split(originalMat, channels); // 이것을 사용해도 분해가 됩니다, 하지만 이 경우 single 채널입니다
		SplitChannels();

		// 채널 저장
		DisplayImage(originalMat, 3);
	}
}


void CRGBDlg::DisplayImage(cv::Mat targetMat, int channel)
{
	// dc
	CDC* pDC = nullptr;

	// 컨트롤 가져오기
	pDC = pictureControl.GetDC();

	// 리사이징
	CRect rect;
	cv::Mat tempMat;
	GetDlgItem(ID_Picture)->GetClientRect(&rect);
	cv::resize(targetMat, tempMat, cv::Size(rect.Width(), rect.Height()));

	CImage *image = new CImage();
	image->Create(tempMat.cols, tempMat.rows, targetMat.channels() * 8);

	// 비트맵 정보 구하기
	BITMAPINFO bitmapInfo;
	bitmapInfo.bmiHeader.biSize = sizeof(BITMAPINFOHEADER);
	bitmapInfo.bmiHeader.biPlanes = 1;
	bitmapInfo.bmiHeader.biBitCount = tempMat.channels() * 8;
	bitmapInfo.bmiHeader.biSizeImage = 0;
	bitmapInfo.bmiHeader.biXPelsPerMeter = 0;
	bitmapInfo.bmiHeader.biYPelsPerMeter = 0;
	bitmapInfo.bmiHeader.biClrUsed = 0;
	bitmapInfo.bmiHeader.biClrImportant = 0;
	bitmapInfo.bmiHeader.biCompression = BI_RGB;
	bitmapInfo.bmiHeader.biWidth = tempMat.cols;
	bitmapInfo.bmiHeader.biHeight = -tempMat.rows;

	// 그리기
	StretchDIBits(image->GetDC(),
		0, 0, tempMat.cols, tempMat.rows,
		0, 0, tempMat.cols, tempMat.rows,
		tempMat.data, &bitmapInfo, DIB_RGB_COLORS, SRCCOPY);
	image->BitBlt(::GetDC(pictureControl.m_hWnd), 0, 0);

	if (image)
	{
		image->ReleaseDC();
		delete image;
	}
	tempMat.release();
	ReleaseDC(pDC);
}

void CRGBDlg::SplitChannels()
{
	cv::Mat zFillMat = cv::Mat::zeros(originalMat.size(), CV_8UC1);

	// 채널 분해
	cv::split(originalMat, channels);

	// 싱글채널 생성
	cv::Mat R[] = { zFillMat, zFillMat, channels[0] };
	cv::Mat G[] = { zFillMat, channels[1], zFillMat };
	cv::Mat B[] = { channels[2], zFillMat, zFillMat };

	// 멀티 채널 합병
	cv::merge(R, 3, channels[0]);
	cv::merge(G, 3, channels[1]);
	cv::merge(B, 3, channels[2]);
}

void CRGBDlg::OnBnClickedAllchannelbtn()
{
	// TODO: Add your control notification handler code here
	if(! originalMat.empty())
	DisplayImage(originalMat, 3);
}


void CRGBDlg::OnBnClickedRchannelbtn()
{
	// TODO: Add your control notification handler code here
	if (!originalMat.empty())
	DisplayImage(channels[0], 3);
}


void CRGBDlg::OnBnClickedGchannelbtn()
{
	// TODO: Add your control notification handler code here
	if (!originalMat.empty())
	DisplayImage(channels[1], 3);
}


void CRGBDlg::OnBnClickedBchannelbtn()
{
	// TODO: Add your control notification handler code here
	if (!originalMat.empty())
	DisplayImage(channels[2], 3);
}